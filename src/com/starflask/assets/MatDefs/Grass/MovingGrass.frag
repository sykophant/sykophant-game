
#ifdef TEXTURE
uniform sampler2D m_Texture;
varying vec2 texCoord;
#endif
 

uniform vec4 m_Color;
uniform vec4 color;
varying vec4 v_Color;  //use vertex color, not material

 
void main(void)
{
#ifdef TEXTURE
vec4 texVal = texture2D(m_Texture, texCoord);
gl_FragColor = texVal * v_Color * 0.01;
#else
gl_FragColor =  v_Color;
#endif
}