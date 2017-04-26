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
  float dist;
} VertexOut;

uniform vec4 frustumPlanes[6]; // right left bottom top far near

float distanceFromPlane(in vec4 plane, in vec3 p) {
    // ((dot(plane.xyz, p) + plane.w) < 0)
    return plane.x * p.x + plane.y * p.y + plane.z * p.z + plane.w;
}

bool pointInFrustum(in vec3 p) {
    return distanceFromPlane(frustumPlanes[4], p) > 0;
    /*
    for (int i = 0; i < 6; i++) {
        vec4 plane = frustumPlanes[i];
        //if ((dot(plane.xyz, p) + plane.w) < 0) {
        if (distanceFromPlane(plane, p) <= 0) {
            return false;
        }
    }
    return true;*/
}

float calcDist(in vec3 p) {
    vec4 plane = frustumPlanes[4];
    float minDist = distanceFromPlane(plane, p) - plane.w;
/*
    for (int i = 1; i < 6; i++) {
        plane = frustumPlanes[0];
        minDist = min(distanceFromPlane(plane, p), minDist);
    }
*/
    return minDist;
}

bool foundVertexInsideFrustum() {
    for (int i = 0; i < gl_in.length(); i++) {
        vec4 position = gl_in[i].gl_Position;
        if (pointInFrustum(position.xyz)) {
            return true;
        }
    }
    return false;
}

void main() {
    if (!foundVertexInsideFrustum()) {
      return;
    }

    for (int i = 0; i < gl_in.length(); i++) {
        gl_Position = gl_in[i].gl_Position;
        VertexOut.pass_textureCoords = VertexIn[i].pass_textureCoords;
        VertexOut.surfaceNormal = VertexIn[i].surfaceNormal;
        VertexOut.toLightVector = VertexIn[i].toLightVector;
        VertexOut.toCameraVector = VertexIn[i].toCameraVector;
        VertexOut.dist = calcDist(gl_Position.xyz);

        EmitVertex();
    }
    EndPrimitive();
}