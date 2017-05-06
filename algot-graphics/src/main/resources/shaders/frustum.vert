#version 150

in vec3 position;
in vec3 normal;
in vec4 color;

out VertexData {
  vec4 color;
} VertexOut;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
  gl_Position = projectionMatrix * viewMatrix * vec4(position, 1.0);
  VertexOut.color = color;
}