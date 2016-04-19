varying vec2 texCoord;

varying vec3 AmbientSum;
varying vec4 DiffuseSum;
varying vec3 SpecularSum;

varying vec4 v_Color;

uniform vec4 m_Diffuse;

varying vec2 vertexLightValues;

//#ifdef DIFFUSEMAP
  uniform sampler2D m_DiffuseMap;
//#endif
    



void main()
{
 vec2 newTexCoord = texCoord;


//DIFFUSEMAP WILL ALWAYS BE TRUE
// #ifdef DIFFUSEMAP
      vec4 diffuseColor = texture2D(m_DiffuseMap, newTexCoord);
//    #else
//      vec4 diffuseColor = vec4(1.0);
//    #endif


// #ifdef VERTEX_LIGHTING
       vec2 light = vertexLightValues.xy;
       #ifdef COLORRAMP
           light.x = texture2D(m_ColorRamp, vec2(light.x, 0.0)).r;
           light.y = texture2D(m_ColorRamp, vec2(light.y, 0.0)).r;
       #endif


 #ifdef SPECULARMAP
      vec4 specularColor = texture2D(m_SpecularMap, newTexCoord);
    #else
      vec4 specularColor = vec4(1.0);
    #endif
    
    
    //gl_FragColor = v_Color;
    
    //gl_fragcolor is the actual color that is painted
    //MOST IMPORTANT LINE OF ALL!! everything is just for this!
    
    
    //still need to fix diffusecolor since it is always 0,0,0 now! Trace it thru lighting.frag
    // gl_FragColor.rgb =     1             *      v_Color.rgb  +  //adds vertex color support
   	//						AmbientSum       * diffuseColor.rgb  +
    //                       DiffuseSum.rgb   * diffuseColor.rgb  * vec3(light.x) +
    //                       SpecularSum.rgb * specularColor.rgb * vec3(light.y);
                           
    

	 gl_FragColor.rgb =  v_Color.rgb  * diffuseColor.rgb  *  vec3(light.x) ;
	 
	//gl_FragColor.rgb = diffuseColor.rgb * v_Color.rgb ;
	 
	gl_FragColor.a = diffuseColor.a  ;  //allows transparency - broken?
}