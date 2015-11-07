varying vec4 position;

uniform mat4 u_worldView;
uniform mat4 u_modelViewMatrix;

attribute vec4 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

void main()
{
	gl_Position = u_worldView * a_position;
    position = u_modelViewMatrix * a_position;
}