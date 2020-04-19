#version 330 core

#ifdef GL_ES 
	precision mediump float;  
#endif  

in vec2 v_blurTexCoords[11];

uniform sampler2D u_texture;

out vec4 fragColor;  

void main()                                    
{           
  fragColor = vec4(0.0);
	fragColor += texture(u_texture, v_blurTexCoords[0]) * 0.0093;
  fragColor += texture(u_texture, v_blurTexCoords[1]) * 0.028002;
  fragColor += texture(u_texture, v_blurTexCoords[2]) * 0.065984;
  fragColor += texture(u_texture, v_blurTexCoords[3]) * 0.121703;
  fragColor += texture(u_texture, v_blurTexCoords[4]) * 0.175713;
  fragColor += texture(u_texture, v_blurTexCoords[5]) * 0.198596;
  fragColor += texture(u_texture, v_blurTexCoords[6]) * 0.175713;
  fragColor += texture(u_texture, v_blurTexCoords[7]) * 0.121703;
  fragColor += texture(u_texture, v_blurTexCoords[8]) * 0.065984;
  fragColor += texture(u_texture, v_blurTexCoords[9]) * 0.028002;
  fragColor += texture(u_texture, v_blurTexCoords[10]) * 0.0093;
}