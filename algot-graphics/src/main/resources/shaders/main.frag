#version 150

in VertexData {
  vec2 pass_textureCoords;
  vec3 surfaceNormal;
  vec3 toLightVector;
  vec3 toCameraVector;
  vec3 color;
} VertexIn;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main(void) {
    vec3 unitNormal = normalize(VertexIn.surfaceNormal);
    vec3 unitLightVector = normalize(VertexIn.toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.2);
    vec3 diffuse = brightness * lightColor;

    vec3 unitCameraVector = normalize(VertexIn.toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitCameraVector);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    out_Color = vec4(VertexIn.color, 0.9);

//    out_Color = vec4(diffuse, 1.0) * texture(textureSampler, VertexIn.pass_textureCoords) + vec4(finalSpecular, 1.0);
}