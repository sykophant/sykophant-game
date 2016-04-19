// author: cvlad

varying vec2 uv;

uniform sampler2D m_noise;
uniform vec4 m_color;
uniform float m_noiseAmount;
uniform float m_speed;
uniform float m_lines;
uniform float m_fallOff;

uniform float g_Time;

varying float ypos;
varying float viewAngle;

void main() {
    vec2 uvOffset1 = vec2(g_Time,g_Time*2.0)*m_noiseAmount;
    vec2 uvOffset2 = -vec2(g_Time,g_Time)*m_noiseAmount;
    vec4 noiseColor1 = texture2D(m_noise, uv + uvOffset1);
    vec4 noiseColor2 = texture2D(m_noise, uv + uvOffset2);
    float noise = (dot(noiseColor1, noiseColor2) - 1) * m_noiseAmount;
    float c = sin((ypos*m_lines + g_Time*m_speed + noise)*100.0);
    vec4 col = vec4(c,c,c,c);
    noiseColor1 = texture2D(m_noise, uv*6 + uvOffset1);
    noiseColor2 = texture2D(m_noise, uv*6 + uvOffset2);
    col.a *= clamp(1.3-(noiseColor1.g+noiseColor2.g),0.0,1.0) * pow(viewAngle,m_fallOff) * 15.0;
    gl_FragColor =  col * m_color * 2.0;
}
