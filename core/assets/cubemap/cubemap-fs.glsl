//Fuente: http://stackoverflow.com/questions/21884805/libgdx-0-9-9-apply-cubemap-in-environment

uniform samplerCube u_environmentCubemap;

varying vec2 v_texCoord0;
varying vec3 v_cubeMapUV;

void main() {
    gl_FragColor = vec4(textureCube(u_environmentCubemap, v_cubeMapUV).rgb, 1.0);
}