#version 330 core

layout(location = 0) in vec4 a_position;
layout(location = 1) in vec4 a_color;
layout(location = 2) in vec2 a_texCoord0;
 
uniform mat4 u_projTrans = mat4(1.0);
uniform float u_targetWidth;

out vec2 v_blurTexCoords[11];

void main()                   
{               
   gl_Position =  u_projTrans * a_position; 
   
   // vec2 centerTexCoords = vec2(a_position.xy * 0.5 + 0.5);

   float pixelSize = 1.0 / u_targetWidth;

   for (int i = -5; i <= 5; i++) {
      v_blurTexCoords[i + 5] = a_texCoord0 + vec2(pixelSize * i, 0.0);
   }
}