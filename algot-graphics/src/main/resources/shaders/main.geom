#version 150

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

in VertexData {
  vec3 pass_position;
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

uniform vec4 frustumPlanes[6]; // right left bottom top far near
uniform bool useFrustumCulling;

float distanceFromPlane(in vec4 plane, in vec3 p) {
    return dot(plane.xyz, p) + plane.w;
}

bool pointInFrustum(in vec3 p) {
    for (int i = 0; i < 6; i++) {
        vec4 plane = frustumPlanes[i];
        if (distanceFromPlane(plane, p) <= 0) {
            return false;
        }
    }
    return true;
}

bool foundVertexInsideFrustum() {
    for (int i = 0; i < gl_in.length(); i++) {
        vec3 position = VertexIn[i].pass_position;
        if (pointInFrustum(position)) {
            return true;
        }
    }
    return false;
}

void main() {
    /*if (useFrustumCulling && !foundVertexInsideFrustum()) {
      return;
    }*/

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