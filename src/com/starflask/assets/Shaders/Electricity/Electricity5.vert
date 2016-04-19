// author: cvlad

uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrixInverse;
attribute vec3 inPosition;
attribute vec3 inNormal;
attribute vec2 inTexCoord;

varying vec2 uv;
uniform float m_width;

void main() { 
    vec4 vertex = vec4(inPosition, 1) + vec4(inNormal * m_width, 0);
    gl_Position = g_WorldViewProjectionMatrix * vertex;
    uv = inTexCoord;
}