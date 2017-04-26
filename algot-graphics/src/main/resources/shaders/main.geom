#version 150

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

in VertexData {
  vec2 pass_textureCoords;
  vec3 surfaceNormal;
  vec3 toLightVector;
  vec3 toCameraVector;
} VertexIn[3];

out VertexData {
  vec2 pass_textureCoords;
  vec3 surfaceNormal;
  vec3 toLightVector;
  vec3 toCameraVector;
} VertexOut;

void main() {
    for (int i = 0; i < gl_in.length(); i++) {
        gl_Position = gl_in[i].gl_Position;
        VertexOut.pass_textureCoords = VertexIn[i].pass_textureCoords;
        VertexOut.surfaceNormal = VertexIn[i].surfaceNormal;
        VertexOut.toLightVector = VertexIn[i].toLightVector;
        VertexOut.toCameraVector = VertexIn[i].toCameraVector;

        EmitVertex();
    }
    EndPrimitive();
}