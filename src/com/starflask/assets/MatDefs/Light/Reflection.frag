#import "Common/ShaderLib/Optics.glsllib"

varying vec3 refVec;
varying vec3 rfrRed, rfrGreen, rfrBlue;
varying float refFactor;

uniform ENVMAP m_Texture;

void main(){
    vec4 refColor = Optics_GetEnvColor(m_Texture, refVec);

    vec4 rfrColor = vec4(0.0, 0.0, 0.0, 1.0);
    rfrColor.r = Optics_GetEnvColor(m_Texture, rfrRed).r;
    rfrColor.g = Optics_GetEnvColor(m_Texture, rfrGreen).g;
    rfrColor.b = Optics_GetEnvColor(m_Texture, rfrBlue).b;
    //gl_FragColor = vec4(rfrColor);
    gl_FragColor = mix(rfrColor, refColor, refFactor);
}