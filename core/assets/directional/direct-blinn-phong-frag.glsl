varying vec2 v_texCoords;
varying vec3 normal; //normal eye space
varying vec4 position; //position of point, eye space
varying vec4 positionObject; //position of point, object space

//light uniforms
uniform vec4 direction; //direction of light, WORLD space
uniform vec3 cameraPosition; //position of camera, WORLD space
uniform vec4 lightSpecular;
uniform vec4 lightAmbient;
uniform vec4 lightColor;
uniform vec4 globalAmbient;
uniform mat4 u_viewMatrix;
uniform mat4 u_modelViewMatrixLight;

//material uniforms
uniform vec4 matSpecular, matAmbient, matDiffuse;
uniform float matShininess;
uniform sampler2D u_texture;

void main()
{
    float visibility = 1.;

    //check z
    //decode
    
    vec4 aux = vec4(1., 1./255., 1./65025., 1./160581375.);
    float zLight = dot(aux, gl_Color); //z menor en eye space de la luz
    float zCam = (u_modelViewMatrixLight*position).z; //z en eye space de la luz

    if (zLight < zCam) {
        visibility = 0.;
    }

    vec4 lightDirectionEye = u_viewMatrix*direction;
    vec4 cameraPositionEye = u_viewMatrix*vec4(cameraPosition,1.);

    vec3 L = normalize(lightDirectionEye.xyz);

    // Compute the diffuse term
    float diffuseLight = max(dot(normal,L), 0.0);
    vec4 diffuse = matDiffuse * lightColor * diffuseLight;

    vec3 V = normalize(cameraPositionEye.xyz - position.xyz);
    vec3 H = normalize(L + V);

    // Compute the specular term
    float specularHardness = pow(max(0.0,dot(H,normal)), matShininess);
    vec4 specular = matSpecular * lightSpecular * specularHardness;

    // Compute ambient term
    vec4 ambient = matAmbient * (globalAmbient + lightAmbient);

    gl_FragColor = visibility*texture2D(u_texture, v_texCoords) * (ambient + diffuse + specular);
}
