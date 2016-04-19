// author: cvlad

varying vec2 uv;
varying float viewDir;

uniform sampler2D m_noise;
uniform sampler2D m_ramp;
uniform float m_speed;
uniform float m_fallOff;
uniform vec4 m_outlineColor;
uniform float m_outlineColorFallOff;

uniform float g_Time;

void main() {
    vec2 UV = uv;
    UV.y *= 3;
    UV.x *= 10;
    vec4 noiseColor1 = texture2D(m_noise, UV + vec2(g_Time, g_Time*2)*m_speed);
    vec4 noiseColor2 = texture2D(m_noise, UV - vec2(g_Time*2, g_Time)*m_speed);
    float x = pow(viewDir, m_fallOff)*(dot(noiseColor1, noiseColor2));
    vec4 col = texture2D(m_ramp, vec2(x, 0));
    vec4 outline = m_outlineColor;
    outline.a *= pow(viewDir, m_outlineColorFallOff);
    gl_FragColor = col+outline;
}
