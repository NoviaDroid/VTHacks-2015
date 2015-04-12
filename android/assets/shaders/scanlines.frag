#ifdef GL_ES
	precision mediump float;
#endif

varying vec4 v_color;
uniform sampler2D u_texture;
varying vec3 v_vPosition;
varying vec2 v_texCoords;
 
void main()
{
	vec4 pixel = texture2D( u_texture, v_texCoords);

	if(pixel.a == 0) 
	{
		gl_FragColor = v_color * pixel;
	}
	else 
	{
		vec4 out_col = v_color * pixel;
		    
		if(out_col.a >= 0.5 && floor(mod(v_vPosition.y, 3)) == floor(3/2.0))
		{
	 		out_col = vec4((vec3(out_col.rgb) + vec3(0.0))/2.0, 1.0);    
		}
		    
		out_col.rgb -= 0.07;
		    
		gl_FragColor = out_col;
    }
}