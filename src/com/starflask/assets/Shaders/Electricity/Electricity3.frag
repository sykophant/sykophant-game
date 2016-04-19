// author: cvlad

varying vec2 uv;
varying vec2 scaledUV;
varying float viewDir;

uniform sampler2D m_noise;
uniform float m_speed;
uniform float m_fallOff;
uniform float m_noiseAmount;
uniform vec4 m_color;

uniform float g_Time;

void main() {
    vec4 noiseColor1 = texture2D(m_noise, uv + vec2(g_Time, g_Time*2)*m_speed);
    vec4 noiseColor2 = texture2D(m_noise, uv - vec2(g_Time*2, g_Time)*m_speed);
    float x = dot(noiseColor1, noiseColor2);
    vec4 noiseColor3 = texture2D(m_noise, uv + vec2(g_Time, g_Time)*m_speed);
    vec4 noiseColor4 = texture2D(m_noise, uv - vec2(g_Time, g_Time*2)*m_speed);
    float y = dot(noiseColor3, noiseColor4);
    vec2 sUV = fract(scaledUV + vec2(x,y)*m_noiseAmount);
    vec2 sUV2 = step(0.9, sUV);
    float v = max(sUV2.x,sUV2.y);
    gl_FragColor = m_color*v;
}
