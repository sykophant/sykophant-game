// author: cvlad

uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrixInverse;
attribute vec3 inPosition;
attribute vec3 inNormal;
attribute vec2 inTexCoord;

varying vec2 uv;
varying vec2 scaledUV;
varying float viewDir;

uniform float m_width;
uniform vec2 m_texScale;

void main() { 
    vec4 vertex = vec4(inPosition, 1) + vec4(inNormal * m_width, 0);
    gl_Position = g_WorldViewProjectionMatrix * vertex;
    scaledUV = inTexCoord * m_texScale;
    uv = inTexCoord;
    vec4 objectSpaceCamPos = g_WorldViewMatrixInverse * vec4(0,0,0,1);
    viewDir = 1 - abs(dot(inNormal, normalize(objectSpaceCamPos.xyz - inPosition)));
}