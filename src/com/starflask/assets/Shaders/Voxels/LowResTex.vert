
uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrix;
uniform mat3 g_NormalMatrix;
uniform mat4 g_ViewMatrix;


uniform vec4 m_Ambient;
uniform vec4 m_Diffuse;
//uniform vec4 m_Specular;
//uniform float m_Shininess;

uniform float m_FogStrength;   
uniform float m_MapSize;

uniform vec4 g_LightColor;
uniform vec4 g_LightPosition;
uniform vec4 g_AmbientLightColor;


varying vec3 AmbientSum;

varying vec4 DiffuseSum;

attribute vec3 inPosition;
attribute vec2 inTexCoord;
attribute vec3 inNormal;

// varying vec4 vLightDir;

//varying vec2 vertexLightValues;
//  uniform vec4 g_LightDirection;

varying vec4 fogColor;

varying vec2 texCoord;

varying vec4 v_Color;

attribute vec4 inColor;





  

void main()
{

	//this is not reading the point light properly
	

	//AmbientSum  = (m_Ambient  * g_AmbientLightColor).rgb;
	//why arent point lights working?
	AmbientSum  =  (g_AmbientLightColor.rgb * g_LightColor.rgb );

   vec4 modelSpacePos = vec4(inPosition, 1.0);
   vec3 modelSpaceNorm = inNormal;

   vec3 wvPosition = (g_WorldViewMatrix * modelSpacePos).xyz;
   vec3 wvNormal  = normalize(g_NormalMatrix * modelSpaceNorm);
   vec3 viewDir = normalize(-wvPosition);
   
   
    vec2 posCoord = vec2(inPosition.x ,inPosition.y);
   
   
     

   //( int(posCoord.x/3.0) * int(m_MapSize) ) + int(posCoord.z/3.0)
   
   
   
    
  
   	

	// fogColor = vec4(1,1,1,1);
   
    //lightVec =  vec3(0.9,0.9,0.9) * fogColor;
   
   
   
   
   
    vec4 pos = vec4(inPosition, 1.0);
 
    v_Color = (inColor+128)/255;
  
    gl_Position = g_WorldViewProjectionMatrix * pos;
    texCoord = inTexCoord;
    

   
}