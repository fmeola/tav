varying vec4 position; //position of point, eye space

vec4 pack(const float);

void main()
{
    gl_FragColor = pack((position.z+1.)/2.);
}

vec4
pack(const float value) {
    vec4 bitSh = vec4(256.*256.*256., 256.*256., 256., 1.);
    vec4 bitMask = vec4(0., 1./256., 1./256., 1./256.);
    vec4 res = fract(value * bitSh);
    res -= res.xxyz * bitMask;
    return res;
}

