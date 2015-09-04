

varying vec2 v_texCoords;

uniform mat4 u_worldView;

attribute vec4 a_position;
attribute vec2 a_texCoord0;

void main()
{
	gl_Position = u_worldView * a_position;
	v_texCoords = a_texCoord0;
}



