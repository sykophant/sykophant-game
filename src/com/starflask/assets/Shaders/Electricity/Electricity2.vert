// author: cvlad

uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrixInverse;
attribute vec3 inPosition;
attribute vec3 inNormal;
attribute vec2 inTexCoord;

varying vec2 uv;
varying float ypos;
varying float viewAngle;

uniform float m_width;

void main() { 
    vec4 vertex = vec4(inPosition, 1) + vec4(inNormal * m_width, 0);
    gl_Position = g_WorldViewProjectionMatrix * vertex;
    uv = inTexCoord;
    vec4 objectSpaceCamPos = g_WorldViewMatrixInverse * vec4(0,0,0,1);
    viewAngle = 1 - abs(dot(inNormal, normalize(objectSpaceCamPos.xyz - inPosition)));
    ypos = inPosition.y;
}