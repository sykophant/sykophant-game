 uniform vec4 m_Color;
   #ifdef PATTERNMAP
     uniform sampler2D m_PatternMap;
   #endif

#ifdef ICONSHEET
uniform sampler2D m_IconSheet;
   #endif
   
   varying vec2 iconTexCoord;
   varying vec2 patternTexCoord;

void main(){
    //returning the color of the pixel (here solid blue)
    //- gl_FragColor is the standard GLSL variable that holds the pixel
    //color. It must be filled in the Fragment Shader.
    
    
    #ifdef ICONSHEET
      vec4 iconColor = texture2D(m_IconSheet, iconTexCoord);
   //   diffuseColor.rgb = (1.0-keyColor.a) * diffuseColor.rgb + keyColor.a * m_KeyColor.rgb;
    #endif
    
    #ifdef PATTERNMAP
     vec4 patternColor = texture2D(m_PatternMap, patternTexCoord);
   #endif
    
    
    
    gl_FragColor =  iconColor;
}