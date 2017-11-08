// Attribute variable needs to have it's value set per each shader call
attribute vec4 a_Position;
uniform mat4 u_Matrix;

void main()
{
    gl_Position = u_Matrix * a_Position;
    gl_PointSize = 10.0;
}
