// https://github.com/julienvillegas/libgdx.info-Shader-Shockwave/blob/master/core/src/com/mygdx/game/MyGdxGame.java

#version 330 core

#ifdef GL_ES 
	precision mediump float;  
#endif  

in vec2 v_texCoords;

uniform sampler2D u_texture;
uniform vec2 u_center; // Mouse position
uniform float u_time; // effect elapsed time

out vec4 fragColor;  

void main()                                    
{
    // get pixel coordinates
	vec2 l_texCoords = v_texCoords;
	//vec2 center = vec2(0.5, 0.5);
	vec3 shockParams = vec3(10.0, 0.8, 0.1);

    float offset = (u_time- floor(u_time))/u_time;
	float CurrentTime = (u_time)*(offset);


	//get distance from center
	float distance = distance(v_texCoords, u_center);
	
	if ( (distance <= (CurrentTime + shockParams.z)) && (distance >= (CurrentTime - shockParams.z)) ) {
    	float diff = (distance - CurrentTime);
        float powDiff = 0.0;
    	if(distance>0.05){
    	 powDiff = 1.0 - pow(abs(diff*shockParams.x), shockParams.y);
        }
    	float diffTime = diff  * powDiff; 
    	vec2 diffUV = normalize(v_texCoords-u_center);
    	//Perform the distortion and reduce the effect over time
    	l_texCoords = v_texCoords + ((diffUV * diffTime)/(CurrentTime * distance * 40.0));
	}

	fragColor = texture2D(u_texture, l_texCoords);
}