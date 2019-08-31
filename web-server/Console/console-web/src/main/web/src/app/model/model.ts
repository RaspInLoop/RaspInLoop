import {  SafeHtml } from '@angular/platform-browser';
/**
 * Package data with nested structure.
 */
export interface Package {
  name: string;
  description: string;
  id: string;
  icon: SafeHtml;
  childIds: string[];
  componentIds: string[];
}

export interface Model {
  id: number;
  components: Component[];
  links: Link[];
}

export interface Parameters {
  name: string;
  value: object;
  type: string;
  description: string;
}

export interface Component {
  id: string;
  name: string;
  svgContent: string;
  position: Point;
  size: Size;
  portGroups: PortGroup[];
  description: string;
  htmlDocumentation: SafeHtml;
  parameters: Parameters[];
}

export interface PortGroup {
  ports: Port[];
  definition: PortGroupDefinition;
}

export interface PortGroupDefinition {
  name: string;
  svg: string;
}

export interface Port {
  id: string;
  position: Point;
  description: string;
  orientation: number;
}


export interface Point {
  x: number;
  y: number;
}

export interface Size {
  width: number;
  height: number;
}


export interface Link {

}

