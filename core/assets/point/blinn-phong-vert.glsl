varying vec2 v_texCoords;
varying vec3 normal; //normal eye space
varying vec4 position; //position of point, eye space


uniform mat4 u_worldView;
uniform mat4 u_normalMatrix;

attribute vec4 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

void main()
{
	gl_Position = u_worldView * a_position;
        position = u_worldView * a_position;
	v_texCoords = a_texCoord0;
	normal = normalize(u_normalMatrix * vec4(a_normal,0.0)).xyz;
}



