#version 150

in VertexData {
  vec4 color;
} VertexIn;

out vec4 out_Color;

void main(void) {
    out_Color = vec4(VertexIn.color.xyz, 0.4);
}