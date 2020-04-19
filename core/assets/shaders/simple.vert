#version 330 core

layout(location = 0) in vec4 a_position;
layout(location = 1) in vec4 a_color;
layout(location = 2) in vec2 a_texCoord0;

uniform mat4 u_projTrans = mat4(1.0);

out vec2 v_texCoords;
out vec3 v_position;

void main() {
  v_texCoords = a_texCoord0;
  v_position = vec3(u_projTrans * a_position);
  gl_Position = u_projTrans * a_position;
}