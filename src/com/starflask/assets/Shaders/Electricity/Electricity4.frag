// author: cvlad

varying vec2 uv;

uniform sampler2D m_noise;
uniform float m_speed;
uniform float m_noiseAmount;
uniform vec4 m_color;
uniform float m_thickness;

uniform float g_Time;

void main() {
    vec4 noiseColor1 = texture2D(m_noise, uv*3 + vec2(g_Time, g_Time*2)*m_speed);
    vec4 noiseColor2 = texture2D(m_noise, uv*3 - vec2(g_Time*2, g_Time)*m_speed);
    float x = dot(noiseColor1, noiseColor2);
    vec4 noiseColor3 = texture2D(m_noise, uv*2 + vec2(x,x)*m_noiseAmount + vec2(g_Time, g_Time)*m_speed);
    vec4 noiseColor4 = texture2D(m_noise, uv*2 + vec2(x,x)*m_noiseAmount - vec2(g_Time, g_Time*2)*m_speed);
    vec3 r = (noiseColor1.xyz + noiseColor2.xyz + noiseColor3.xyz + noiseColor4.xyz)/4;
    float rr = (r.g + r.b)/2;
    rr = max(rr-0.03,0);
    rr = pow(rr+0.4,30);
    rr = step(0.5-m_thickness,rr)*step(rr,0.5+m_thickness);
    gl_FragColor = m_color*rr;
}
