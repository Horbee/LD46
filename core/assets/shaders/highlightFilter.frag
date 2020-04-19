#version 330 core

in vec2 v_texCoords;

uniform sampler2D u_texture;

out vec4 fragColor;

void main() {
  vec4 color = texture(u_texture, v_texCoords);
  float brightness = (color.r * 0.2126) + (color.g * 0.7152) + (color.b * 0.0722);
  fragColor = color * brightness;
}