#version 330 core

#ifdef GL_ES 
	precision mediump float;  
#endif  

in vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_highlightTexture;

out vec4 fragColor;  

void main()                                    
{
    vec4 sceneColor = texture(u_texture, v_texCoords); 
    vec4 highlightColor = texture(u_highlightTexture, v_texCoords);

    fragColor = sceneColor + highlightColor;
}