#version 310 es
precision mediump float;
out vec4 outColor;

in vec3 passColor;

void main() {
	outColor = vec4(passColor, 1);
}
