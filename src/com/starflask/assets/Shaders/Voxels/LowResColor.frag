

//varying vec3 AmbientSum;

//varying vec3 SpecularSum;

varying vec4 v_Color;


//could optimize by passing only the byte and the pallette array to the shader! Much less color data would be streamed


varying vec2 vertexLightValues;

//varying float fogMult;


void main()
{

    vec2 light = vertexLightValues.xy;      
 
	gl_FragColor.rgb =  v_Color.rgb ;
	 
	gl_FragColor.a = v_Color.a  ;  //allows transparency - broken?
}