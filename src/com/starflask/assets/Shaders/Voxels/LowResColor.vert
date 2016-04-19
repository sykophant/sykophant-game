//GREATLY affects FPS! being a shader and all..



uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrix;
uniform mat3 g_NormalMatrix;
uniform mat4 g_ViewMatrix;



uniform vec4 m_Ambient;
uniform vec4 m_Diffuse;
//uniform vec4 m_Specular;
//uniform float m_Shininess;

//uniform vec4 g_LightColor;
//uniform vec4 g_LightPosition;
//uniform vec4 g_AmbientLightColor;


//varying vec4 DiffuseSum;

attribute vec3 inPosition;
attribute vec2 inTexCoord;
attribute vec3 inNormal;
attribute vec4 inColor;  //passed in thru GL Buffer

 varying vec4 vLightDir;

varying vec2 vertexLightValues;
uniform vec4 g_LightDirection;

varying vec3 lightVec;

//varying vec2 texCoord;

varying vec4 v_Color;



//uniform vec4 m_WarFog;
//varying float fogMult;
  

void main()
{
   //vec4 modelSpacePos = vec4(inPosition, 1.0);
   //vec3 modelSpaceNorm = inNormal;

  // vec3 wvPosition = (g_WorldViewMatrix * modelSpacePos).xyz;
  // vec3 wvNormal  = normalize(g_NormalMatrix * modelSpaceNorm);
  // vec3 viewDir = normalize(-wvPosition);
   
  
 //  fogMult = 0.8;
   
   
   //if(fog) //boolean
   //{
   //fogMult = 0.4f;
   //}
   
   //vec4 wvLightPos = (g_ViewMatrix * vec4(g_LightPosition.xyz,clamp(g_LightColor.w,0.0,1.0)));
   //wvLightPos.w = g_LightPosition.w;
   // vec4 lightColor = g_LightColor;
   
    vec4 pos = vec4(inPosition, 1.0);
 
 //this is the issue
    //v_Color = (inColor+128.0)/255.0;
  	v_Color = inColor;
   
  
    gl_Position = g_WorldViewProjectionMatrix * pos;  
   // texCoord = inTexCoord;
    
   //  lightColor.w = 1.0;
   // DiffuseSum  =  m_Diffuse  * lightColor;
   // DiffuseSum  =  lightColor;
   // DiffuseSum *= inColor;//not working right
   
   //lightComputeDir(wvPosition, lightColor, wvLightPos, vLightDir);
   
   //vertexLightValues = computeLighting(wvPosition, wvNormal, viewDir, wvLightPos);
}