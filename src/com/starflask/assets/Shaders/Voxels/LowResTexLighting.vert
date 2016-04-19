
uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrix;
uniform mat3 g_NormalMatrix;
uniform mat4 g_ViewMatrix;

uniform vec4 m_Ambient;
uniform vec4 m_Diffuse;
uniform vec4 m_Specular;
uniform float m_Shininess;

uniform vec4 g_LightColor;
uniform vec4 g_LightPosition;
uniform vec4 g_AmbientLightColor;


varying vec4 DiffuseSum;

attribute vec3 inPosition;
attribute vec2 inTexCoord;
attribute vec3 inNormal;

 varying vec4 vLightDir;

varying vec2 vertexLightValues;
  uniform vec4 g_LightDirection;

varying vec3 lightVec;

varying vec2 texCoord;

varying vec4 v_Color;

attribute vec4 inColor;


float lightComputeDiffuse(in vec3 norm, in vec3 lightdir){
      return max(0.0, dot(norm, lightdir));
  }

  float lightComputeSpecular(in vec3 norm, in vec3 viewdir, in vec3 lightdir, in float shiny){
      if (shiny <= 1.0){
          return 0.0;
      }
      #ifndef LOW_QUALITY
        vec3 H = (viewdir + lightdir) * vec3(0.5);
        return pow(max(dot(H, norm), 0.0), shiny);
      #else
        return 0.0;
      #endif
  }


  // JME3 lights in world space
void lightComputeDir(in vec3 worldPos, in vec4 color, in vec4 position, out vec4 lightDir){
    float posLight = step(0.5, color.w);
    vec3 tempVec = position.xyz * sign(posLight - 0.5) - (worldPos * posLight);
    lightVec = tempVec;  
    #ifdef ATTENUATION
     float dist = length(tempVec);
     lightDir.w = clamp(1.0 - position.w * dist * posLight, 0.0, 1.0);
     lightDir.xyz = tempVec / vec3(dist);
    #else
     lightDir = vec4(normalize(tempVec), 1.0);
    #endif
}

vec2 computeLighting(in vec3 wvPos, in vec3 wvNorm, in vec3 wvViewDir, in vec4 wvLightPos){
     vec4 lightDir;
     lightComputeDir(wvPos, g_LightColor, wvLightPos, lightDir);
     float spotFallOff = 1.0;
     if(g_LightDirection.w != 0.0){
          vec3 L=normalize(lightVec.xyz);
          vec3 spotdir = normalize(g_LightDirection.xyz);
          float curAngleCos = dot(-L, spotdir);    
          float innerAngleCos = floor(g_LightDirection.w) * 0.001;
          float outerAngleCos = fract(g_LightDirection.w);
          float innerMinusOuter = innerAngleCos - outerAngleCos;
          spotFallOff = clamp((curAngleCos - outerAngleCos) / innerMinusOuter, 0.0, 1.0);
     }
     float diffuseFactor = lightComputeDiffuse(wvNorm, lightDir.xyz);
     float specularFactor = lightComputeSpecular(wvNorm, wvViewDir, lightDir.xyz, m_Shininess);
     //specularFactor *= step(0.01, diffuseFactor);
     return vec2(diffuseFactor, specularFactor) * vec2(lightDir.w)*spotFallOff;
  }
  

  

void main()
{
   vec4 modelSpacePos = vec4(inPosition, 1.0);
   vec3 modelSpaceNorm = inNormal;

   vec3 wvPosition = (g_WorldViewMatrix * modelSpacePos).xyz;
   vec3 wvNormal  = normalize(g_NormalMatrix * modelSpaceNorm);
   vec3 viewDir = normalize(-wvPosition);
   
   vec4 wvLightPos = (g_ViewMatrix * vec4(g_LightPosition.xyz,clamp(g_LightColor.w,0.0,1.0)));
   wvLightPos.w = g_LightPosition.w;
    vec4 lightColor = g_LightColor;
   
    vec4 pos = vec4(inPosition, 1.0);
 
    v_Color = (inColor+128)/255;
  
    gl_Position = g_WorldViewProjectionMatrix * pos;
    texCoord = inTexCoord;
    
     lightColor.w = 1.0;
    DiffuseSum  =  lightColor;
   
   lightComputeDir(wvPosition, lightColor, wvLightPos, vLightDir);
   
   vertexLightValues = computeLighting(wvPosition, wvNormal, viewDir, wvLightPos);
}