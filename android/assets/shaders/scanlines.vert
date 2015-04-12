uniform mat4 u_projTrans;
attribute vec3 a_position;                
attribute vec4 a_color;                
attribute vec2 a_texCoord0;             
varying vec3 v_vPosition;
varying vec2 v_texCoords;
varying vec4 v_color;
 
void main()
{
    vec4 object_space_pos = vec4( a_position.x, a_position.y, a_position.z, 1.0);
    gl_Position = u_projTrans * object_space_pos;
    v_vPosition = a_position;
    v_color = a_color;
    v_texCoords = a_texCoord0;
}