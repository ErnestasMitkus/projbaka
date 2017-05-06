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
  vec3 color;
} VertexOut;

uniform vec4 frustumPlanes[6]; // right left bottom top far near

const int TEST_PLANE = 5;

/*
float distanceFromPlane(in vec4 plane, in vec3 p) {
    // ((dot(plane.xyz, p) + plane.w) < 0)
    return plane.x * p.x + plane.y * p.y + plane.z * p.z + plane.w;
}
*/


float distanceFromPlane(in vec4 plane, in vec3 p) {
    float flatDist = plane.x * p.x + plane.y * p.y + plane.z * p.z + plane.w;
    return flatDist / sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
}

bool pointInFrustum(in vec3 p) {
    //return distanceFromPlane(frustumPlanes[TEST_PLANE], p) > 0;

    for (int i = 0; i < 6; i++) {
        vec4 plane = frustumPlanes[i];
        //if ((dot(plane.xyz, p) + plane.w) < 0) {
        if (distanceFromPlane(plane, p) <= 0) {
            return false;
        }
    }
    return true;
}

float calcDist(in vec3 p) {
    vec4 plane = frustumPlanes[0];
    float minDist = distanceFromPlane(plane, p) - plane.w;

    for (int i = 1; i < 6; i++) {
        plane = frustumPlanes[i];
        minDist = min(distanceFromPlane(plane, p) - plane.w, minDist);
    }

    return minDist;
}

int calcCount(in vec3 p) {
    return distanceFromPlane(frustumPlanes[TEST_PLANE], p) > 0 ? 1 : 0;
/*
    int count = 0;
    for (int i = 0; i < 6; i++) {
        vec4 plane = frustumPlanes[i];
        float dist = distanceFromPlane(plane, p);
        if (dist > 0) {
            count++;
        }
    }
    return count;*/
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

vec3 colorFromCount(int count) {
  switch(count) {
    case 1: return vec3(1, 0, 0);
    case 2: return vec3(0, 1, 0);
    case 3: return vec3(0, 0, 1);
    case 4: return vec3(1, 1, 0);
    case 5: return vec3(1, 0, 1);
    case 6: return vec3(1, 1, 1);
    default: return vec3(0, 0, 0);
  }
}


void main() {
    /*if (!foundVertexInsideFrustum()) {
      return;
    }*/

    int count = 0;
    for (int i = 0; i < gl_in.length(); i++) {
        count = max(calcCount(gl_in[i].gl_Position.xyz), count);
    }

    for (int i = 0; i < gl_in.length(); i++) {
        gl_Position = gl_in[i].gl_Position;
        VertexOut.pass_textureCoords = VertexIn[i].pass_textureCoords;
        VertexOut.surfaceNormal = VertexIn[i].surfaceNormal;
        VertexOut.toLightVector = VertexIn[i].toLightVector;
        VertexOut.toCameraVector = VertexIn[i].toCameraVector;




        //VertexOut.color = colorFromCount(count);
        VertexOut.color = vec3(1, 0, 1);


        float dist = distanceFromPlane(frustumPlanes[TEST_PLANE], gl_in[i].gl_Position.xyz);
        float perc = min(max(dist / 100.0, 0.0), 1.0);
        VertexOut.color = vec3(0, 0, perc);

        /*if (perc >= 1) {
          VertexOut.color = vec3(0, 1, 1);
        }

        if (perc <= 0) {
          VertexOut.color = vec3(1, 1, 0);
        }*/

        if (dist > 0) {
          VertexOut.color = vec3(1, 0, 0);
        }

        if (dist > 1) {
          VertexOut.color = vec3(1, 1, 0);
        }

        if (dist > 3) {
          VertexOut.color = vec3(0, 1, 0);
        }

        if (dist > 6) {
          VertexOut.color = vec3(0, 1, 1);
        }

        if (dist <= 0) {
          VertexOut.color = vec3(1, 1, 1);
        }

        EmitVertex();
    }
    EndPrimitive();
}