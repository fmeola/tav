//Firstly, we need to define loads of new attributes, one for each bone.
attribute vec2 a_boneWeight0;
attribute vec2 a_boneWeight1;
attribute vec2 a_boneWeight2;
attribute vec2 a_boneWeight3;
attribute vec2 a_boneWeight4;
attribute vec2 a_boneWeight5;
attribute vec2 a_boneWeight6;
attribute vec2 a_boneWeight7;
attribute vec2 a_boneWeight8;
attribute vec2 a_boneWeight9;
attribute vec2 a_boneWeight10;
attribute vec2 a_boneWeight11;

//We also need to take the bonematrices
uniform mat4 u_bones[12];

void main() {
    // Calculate skinning for each vertex
    mat4 skinning = mat4(0.0);
    skinning += (a_boneWeight0.y) * u_bones[int(a_boneWeight0.x)];
    skinning += (a_boneWeight1.y) * u_bones[int(a_boneWeight1.x)];
    skinning += (a_boneWeight2.y) * u_bones[int(a_boneWeight2.x)];
    skinning += (a_boneWeight3.y) * u_bones[int(a_boneWeight3.x)];
    skinning += (a_boneWeight4.y) * u_bones[int(a_boneWeight4.x)];
    skinning += (a_boneWeight5.y) * u_bones[int(a_boneWeight5.x)];
    skinning += (a_boneWeight6.y) * u_bones[int(a_boneWeight6.x)];
    skinning += (a_boneWeight7.y) * u_bones[int(a_boneWeight7.x)];
    skinning += (a_boneWeight8.y) * u_bones[int(a_boneWeight8.x)];
    skinning += (a_boneWeight9.y) * u_bones[int(a_boneWeight9.x)];
    skinning += (a_boneWeight10.y) * u_bones[int(a_boneWeight10.x)];
    skinning += (a_boneWeight11.y) * u_bones[int(a_boneWeight11.x)];

    //Include skinning into the modelspace position
    vec4 pos = skinning * vec4(a_position,1.0);

    // Rest of code is justlike usual
    v = vec3((u_modelViewMatrix * pos).xyz);
    vsN = normalize(vec3(u_normalMatrix * skinning * vec4(a_normal, 0.0)).xyz); //viewspaceNormal

    gl_Position = u_mvpMatrix * pos;
    v_texCoord = a_texCoord0;
}