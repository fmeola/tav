attribute vec2 a_boneWeight0;
attribute vec2 a_boneWeight1;

attribute vec4 a_position;
attribute vec2 a_texCoord0;
attribute vec4 pos;

uniform mat4 u_bones[32];
uniform mat4 u_mvpMatrix;

varying vec2 v_texCoords;

void main() {
    mat4 skinning = mat4(0.0);

    skinning += (a_boneWeight0.y) * u_bones[int(a_boneWeight0.x)];
    skinning += (a_boneWeight1.y) * u_bones[int(a_boneWeight1.x)];

    vec4 pos = skinning * vec4(a_position.x, a_position.y, a_position.z, 1.0);

    gl_Position = u_mvpMatrix * pos;
    v_texCoords = a_texCoord0;
}