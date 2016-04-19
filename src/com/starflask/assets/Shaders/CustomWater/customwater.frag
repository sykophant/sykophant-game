#import "Common/ShaderLib/WaterUtil.glsllib"
// Water pixel shader
// Copyright (C) JMonkeyEngine 3.0
// by Remy Bouquet (nehon) for JMonkeyEngine 3.0
// original HLSL version by Wojciech Toman 2009

uniform sampler2D m_HeightMap;
uniform sampler2D m_Texture;
uniform sampler2D m_DepthTexture;
uniform sampler2D m_NormalMap;
uniform sampler2D m_FoamMap;
uniform sampler2D m_CausticsMap;
uniform sampler2D m_ReflectionMap;

uniform mat4 g_ViewProjectionMatrixInverse;
uniform mat4 m_TextureProjMatrix;
uniform vec3 m_CameraPosition;

uniform float m_WaterHeight;
uniform float m_Time;
uniform float m_WaterTransparency;
uniform float m_NormalScale;
uniform float m_R0;
uniform float m_MaxAmplitude;
uniform vec3 m_LightDir;
uniform vec4 m_LightColor;
uniform float m_ShoreHardness;
uniform float m_FoamHardness;
uniform float m_RefractionStrength;
uniform vec3 m_FoamExistence;
uniform vec3 m_ColorExtinction;
uniform float m_Shininess;
uniform vec4 m_WaterColor;
uniform vec4 m_DeepWaterColor;
uniform vec2 m_WindDirection;
uniform float m_SunScale;
uniform float m_WaveScale;
uniform float m_UnderWaterFogDistance;
uniform float m_CausticsIntensity;
#ifdef ENABLE_AREA
uniform vec3 m_Center;
uniform float m_Radius;
#endif

vec2 scale = vec2(m_WaveScale, m_WaveScale);
float refractionScale = m_WaveScale;

// Modifies 4 sampled normals. Increase first values to have more
// smaller "waves" or last to have more bigger "waves"
const vec4 normalModifier = vec4(3.0, 2.0, 4.0, 10.0);
// Strength of displacement along normal.
// Strength of displacement along normal.
uniform float m_ReflectionDisplace;
// Water transparency along eye vector.
const float visibility = 3.0;
// foam intensity
uniform float m_FoamIntensity ;

varying vec2 texCoord;

mat3 MatrixInverse(in mat3 inMatrix){  
    float det = dot(cross(inMatrix[0], inMatrix[1]), inMatrix[2]);
    mat3 T = transpose(inMatrix);
    return mat3(cross(T[1], T[2]),
        cross(T[2], T[0]),
        cross(T[0], T[1])) / det;
}


mat3 computeTangentFrame(in vec3 N, in vec3 P, in vec2 UV) {
    vec3 dp1 = dFdx(P);
    vec3 dp2 = dFdy(P);
    vec2 duv1 = dFdx(UV);
    vec2 duv2 = dFdy(UV);

    // solve the linear system
    mat3 M = mat3(dp1, dp2, cross(dp1, dp2));
    //vec3 dp1xdp2 = cross(dp1, dp2);
    mat3 inverseM = MatrixInverse(M);
    //mat2x3 inverseM = mat2x3(cross(dp2, dp1xdp2), cross(dp1xdp2, dp1));

    vec3 T = inverseM * vec3(duv1.x, duv2.x, 0.0);
    vec3 B = inverseM * vec3(duv1.y, duv2.y, 0.0);

    //vec3 T = inverseM * vec2(duv1.x, duv2.x);
    //vec3 B = inverseM * vec2(duv1.y, duv2.y);

    // construct tangent frame
    float maxLength = max(length(T), length(B));
    T = T / maxLength;
    B = B / maxLength;

    //vec3 tangent = normalize(T);
    //vec3 binormal = normalize(B);

    return mat3(T, B, N);
}

float saturate(in float val){
    return clamp(val,0.0,1.0);
}

vec3 saturate(in vec3 val){
    return clamp(val,vec3(0.0),vec3(1.0));
}


vec3 getPosition(in float depth, in vec2 uv){
    vec4 pos = vec4(uv, depth, 1.0) * 2.0 - 1.0;
    pos = g_ViewProjectionMatrixInverse * pos;
    return pos.xyz / pos.w;
}

// Function calculating fresnel term.
// - normal - normalized normal vector
// - eyeVec - normalized eye vector
float fresnelTerm(in vec3 normal,in vec3 eyeVec){
    float angle = 1.0 - saturate(dot(normal, eyeVec));
    float fresnel = angle * angle;
    fresnel = fresnel * fresnel;
    fresnel = fresnel * angle;
    return saturate(fresnel * (1.0 - saturate(m_R0)) + m_R0 - m_RefractionStrength);
}

vec2 m_FrustumNearFar=vec2(1.0,m_UnderWaterFogDistance);
const float LOG2 = 1.442695;



void main(){
    float sceneDepth = texture2D(m_DepthTexture, texCoord).r;
    float isAtFarPlane = step(0.99998, sceneDepth);

    vec3 color2 = texture2D(m_Texture, texCoord).rgb;
    vec3 color = color2;
    
 	vec3 position = getPosition(sceneDepth,texCoord);
    float level = m_WaterHeight;
    
      vec3 eyeVec = position - m_CameraPosition;
    float diff = level - position.y;
    float cameraDepth = m_CameraPosition.y - position.y;

    // Find intersection with water surface
    vec3 eyeVecNorm = normalize(eyeVec);
    
     float t = (level - m_CameraPosition.y) / eyeVecNorm.y;
    vec3 surfacePoint = m_CameraPosition + eyeVecNorm * t;
    
    
    vec3 waterPosition = position;
     vec4 texCoordProj = m_TextureProjMatrix * vec4(waterPosition, 1.0);
       vec3 normal = vec3(0.0);
     
     
     

  vec3 reflection = texture2D(m_ReflectionMap, texCoordProj.xy).rgb;
	
    float depth = length(position - surfacePoint);
    float depth2 = surfacePoint.y - position.y;

 vec3 refraction = color2;
    #ifdef ENABLE_REFRACTION
        texC = texCoord.xy;
        texC += sin(m_Time*1.8  + 3.0 * abs(position.y)) * (refractionScale * min(depth2, 1.0));
        texC = clamp(texC,0.0,1.0);
        refraction = texture2D(m_Texture, texC).rgb;
    #endif

  float fresnel = fresnelTerm(normal, eyeVecNorm);

   float depthN = depth * m_WaterTransparency;
    float waterCol = saturate(length(m_LightColor.rgb) / m_SunScale);
    refraction = mix(mix(refraction, m_WaterColor.rgb * waterCol, saturate(depthN / visibility)),
        m_DeepWaterColor.rgb * waterCol, saturate(depth2 / m_ColorExtinction));


	 color = mix(refraction, reflection, fresnel);
    color = mix(refraction, color, saturate(depth * m_ShoreHardness));
    color = saturate(color + max(specular, foam ));
    color = mix(refraction, color, saturate(depth* m_FoamHardness));color = mix(refraction, reflection, fresnel);

    // XXX: HACK ALERT:
    // We trick the GeForces to think they have
    // to calculate the derivatives for all these pixels by using step()!
    // That way we won't get pixels around the edges of the terrain,
    // Where the derivatives are undefined
    if(position.y > level){
    //        color = color2;
    }

	vec3 mycolor = vec3(0.4, 0.4, 0.8);
	vec3 finalcolor = (color.rgb + mycolor.rgb)/2;
	
    gl_FragColor = vec4(finalcolor.rgb.rgb,0.5);
    
}