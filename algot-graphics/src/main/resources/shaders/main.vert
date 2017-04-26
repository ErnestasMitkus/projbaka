#version 150

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out VertexData {
  vec2 pass_textureCoords;
  vec3 surfaceNormal;
  vec3 toLightVector;
  vec3 toCameraVector;
} VertexOut;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition;

void main(void) {
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    VertexOut.pass_textureCoords = textureCoords;

    VertexOut.surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
    VertexOut.toLightVector = lightPosition - worldPosition.xyz;

    VertexOut.toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
}