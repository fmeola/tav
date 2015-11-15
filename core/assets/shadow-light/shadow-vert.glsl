varying vec4 position; //position of point, eye space

uniform mat4 u_modelViewProjectionMatrix;
uniform mat4 u_modelViewMatrix;

attribute vec4 a_position;
attribute vec3 a_normal;

void main()
{
        position = u_modelViewMatrix * a_position;
	gl_Position = u_modelViewProjectionMatrix * a_position;
}