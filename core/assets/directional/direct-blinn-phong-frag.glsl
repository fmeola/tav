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
uniform mat4 u_modelViewProjectionMatrixLight;

//material uniforms
uniform vec4 matSpecular, matAmbient, matDiffuse;
uniform float matShininess;
uniform sampler2D u_texture;
uniform sampler2D u_shadowMap;

void main()
{
    float visibility = 1.;

    //check z
    //decode
    
    vec4 aux = vec4(1., 1./255., 1./65025., 1./160581375.);
    vec4 posFromLight = (u_modelViewProjectionMatrixLight*positionObject);
    vec2 shadowCords = (posFromLight.xy+1.)/2.;
    vec4 shadowColor = texture2D(u_shadowMap, shadowCords);
    //z menor vista desde la luz
    float zShadow = dot(shadowColor, aux); //z mas cercana a la luz (eye space de la luz)
    //z actual vista desde la luz
    float zLight = -posFromLight.z;

    if (zLight < zShadow-0.005) {
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
