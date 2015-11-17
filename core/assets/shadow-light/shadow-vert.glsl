varying vec4 position;

uniform mat4 u_modelViewProjectionMatrix;

attribute vec4 a_position;
attribute vec3 a_normal;

void main()
{
    position = u_modelViewProjectionMatrix * a_position;
	gl_Position = u_modelViewProjectionMatrix * a_position;
}
