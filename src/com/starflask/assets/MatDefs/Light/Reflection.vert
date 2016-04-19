uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldMatrix;
uniform mat3 g_NormalMatrix;
uniform vec3 g_CameraPosition;

attribute vec3 inPosition;
attribute vec3 inNormal;

varying vec3 refVec;
varying vec3 rfrRed, rfrGreen, rfrBlue;
varying float refFactor;

void main(){
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition,1.0);

    vec3 worldPos = (g_WorldMatrix * vec4(inPosition,1.0)).xyz;

    // WARNING: Not allowed to create matrix from matrix
    // mat3 worldMat3 = mat3(g_WorldMatrix);

    vec3 I = normalize(worldPos - g_CameraPosition).xyz;
    //vec3 N = normalize(worldMat3 * inNormal);
    vec3 N = normalize( (g_WorldMatrix * vec4(inNormal, 0.0)).xyz );

    float fresnelBias = 0.05;
    float fresnelScale = 0.25;
    float fresnelPower = 0.30;
    vec3 etaRGB = vec3(0.86, 0.88, 0.91);
    etaRGB = vec3(0.93, 0.94, 0.95);
    //etaRGB = vec3(1.0);

    refFactor = fresnelBias + fresnelScale * pow(1.0 + dot(I, N), fresnelPower);

    refVec   = reflect(I, N);
    rfrRed   = refract(I, N, etaRGB.r);
    rfrGreen = refract(I, N, etaRGB.g);
    rfrBlue  = refract(I, N, etaRGB.b);
    //rfrRed   = E;
    //rfrGreen = E;
    //rfrBlue  = E;
}