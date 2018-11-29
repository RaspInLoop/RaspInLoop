import { Injectable } from '@angular/core';
import { Graph } from './model/graph';
import { GraphDescription } from './model/graphdescription';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';

export const GRAPH_DESCRITPION: GraphDescription[] = [
  { id: 11, name: 'Mr. Nice' , creationDate:"12/06/2018", description: "le graph 11"},
  { id: 12, name: 'Narco' , creationDate:"12/06/2018", description: "le graph 12"},
  { id: 13, name: 'Bombasto' , creationDate:"12/06/2018", description: "le graph 13"},
  { id: 14, name: 'Celeritas' , creationDate:"12/06/2018", description: "le graph 14"},
  { id: 15, name: 'Magneta' , creationDate:"12/06/2018", description: "le graph 15"},
  { id: 16, name: 'RubberMan' , creationDate:"12/06/2018", description: "le graph 16"},
  { id: 17, name: 'Dynama' , creationDate:"12/06/2018", description: "le graph 17"},
  { id: 18, name: 'Dr IQ' , creationDate:"12/06/2018", description: "le graph 18"},
  { id: 19, name: 'Magma' , creationDate:"12/06/2018", description: "le graph 19"},
  { id: 20, name: 'Tornado' , creationDate:"12/06/2018", description: "le graph 20"}
];

export const GRAPHS: Graph[] = [
  { id: 11, json:JSON.stringify({
    "graph": [
        {
            "value": {
                "name": "Main",
                "mainNode": "true",
                "type": "component"
            },
            "geometry": {
                "x": 10,
                "y": 50,
                "width": 190,
                "height": 280,
                "relative": false
            },
            "style": "main;",
            "id": "2",
            "vertex": true,
            "connectable": false,
            "parent": "1",
            "source": null,
            "target": null,
            "children": [
                {
                    "value": "PORT_Transitions",
                    "geometry": {
                        "x": 1,
                        "y": 0.25,
                        "width": 32,
                        "height": 32,
                        "relative": true,
                        "offset": {
                            "x": -16,
                            "y": -16
                        }
                    },
                    "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
                    "id": "3",
                    "vertex": true,
                    "connectable": true,
                    "parent": "2",
                    "source": null,
                    "target": null,
                    "collapsed": false,
                    "children": [
                        {
                            "value": {
                                "name": "transition 1",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": -44
                                }
                            },
                            "style": "transition;",
                            "id": "4",
                            "vertex": true,
                            "connectable": true,
                            "parent": "3",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#8",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "31",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "4",
                                    "target": "18",
                                    "mxObjectId": "mxCell#12",
                                    "collapsed": false
                                }
                            ],
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 2",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": -14
                                }
                            },
                            "style": "transition;",
                            "id": "5",
                            "vertex": true,
                            "connectable": true,
                            "parent": "3",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#9",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "33",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "5",
                                    "target": "23",
                                    "mxObjectId": "mxCell#13"
                                }
                            ],
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 3",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": 16
                                }
                            },
                            "style": "transition;",
                            "id": "6",
                            "vertex": true,
                            "connectable": true,
                            "parent": "3",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#10",
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 4",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": 46
                                }
                            },
                            "style": "transition;",
                            "id": "7",
                            "vertex": true,
                            "connectable": true,
                            "parent": "3",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#11"
                        }
                    ],
                    "edges": [],
                    "mxObjectId": "mxCell#7"
                },
                {
                    "value": "PORT_Actions",
                    "geometry": {
                        "x": 1,
                        "y": 0.75,
                        "width": 32,
                        "height": 32,
                        "relative": true,
                        "offset": {
                            "x": -16,
                            "y": -8
                        }
                    },
                    "style": "port_actions;image=https://cloud.githubusercontent.com/assets/6003532/20865730/414b06a2-ba55-11e6-85f9-1ccbc0c30a73.png;align=right;imageAlign=right;spacingRight=18",
                    "id": "8",
                    "vertex": true,
                    "connectable": true,
                    "parent": "2",
                    "source": null,
                    "target": null,
                    "collapsed": false,
                    "children": [
                        {
                            "value": {
                                "name": "action 1",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": -44
                                }
                            },
                            "style": "action;",
                            "id": "9",
                            "vertex": true,
                            "connectable": true,
                            "parent": "8",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#15"
                        },
                        {
                            "value": {
                                "name": "action 2",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": -14
                                }
                            },
                            "style": "action;",
                            "id": "10",
                            "vertex": true,
                            "connectable": true,
                            "parent": "8",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#16",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "32",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "10",
                                    "target": "16",
                                    "mxObjectId": "mxCell#19",
                                    "collapsed": false
                                }
                            ],
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "action 3",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": 16
                                }
                            },
                            "style": "action;",
                            "id": "11",
                            "vertex": true,
                            "connectable": true,
                            "parent": "8",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#17",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "35",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "11",
                                    "target": "24",
                                    "mxObjectId": "mxCell#21",
                                    "collapsed": true
                                }
                            ],
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "action 4",
                                "type": "state"
                            },
                            "geometry": {
                                "x": 1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": 2,
                                    "y": 46
                                }
                            },
                            "style": "action;",
                            "id": "12",
                            "vertex": true,
                            "connectable": true,
                            "parent": "8",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#18",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "34",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "12",
                                    "target": "29",
                                    "mxObjectId": "mxCell#20",
                                    "collapsed": false
                                }
                            ],
                            "collapsed": true
                        }
                    ],
                    "edges": [],
                    "mxObjectId": "mxCell#14"
                }
            ],
            "mxObjectId": "mxCell#6"
        },
        {
            "value": "PORT_Transitions",
            "geometry": {
                "x": 1,
                "y": 0.25,
                "width": 32,
                "height": 32,
                "relative": true,
                "offset": {
                    "x": -16,
                    "y": -16
                }
            },
            "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
            "id": "3",
            "vertex": true,
            "connectable": true,
            "parent": "2",
            "source": null,
            "target": null,
            "collapsed": false,
            "children": [
                {
                    "value": {
                        "name": "transition 1",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": -44
                        }
                    },
                    "style": "transition;",
                    "id": "4",
                    "vertex": true,
                    "connectable": true,
                    "parent": "3",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#8",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "31",
                            "edge": true,
                            "parent": "1",
                            "source": "4",
                            "target": "18",
                            "mxObjectId": "mxCell#12",
                            "collapsed": false
                        }
                    ],
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 2",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": -14
                        }
                    },
                    "style": "transition;",
                    "id": "5",
                    "vertex": true,
                    "connectable": true,
                    "parent": "3",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#9",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "33",
                            "edge": true,
                            "parent": "1",
                            "source": "5",
                            "target": "23",
                            "mxObjectId": "mxCell#13"
                        }
                    ],
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 3",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": 16
                        }
                    },
                    "style": "transition;",
                    "id": "6",
                    "vertex": true,
                    "connectable": true,
                    "parent": "3",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#10",
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 4",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": 46
                        }
                    },
                    "style": "transition;",
                    "id": "7",
                    "vertex": true,
                    "connectable": true,
                    "parent": "3",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#11"
                }
            ],
            "edges": [],
            "mxObjectId": "mxCell#7"
        },
        {
            "value": {
                "name": "transition 1",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": -44
                }
            },
            "style": "transition;",
            "id": "4",
            "vertex": true,
            "connectable": true,
            "parent": "3",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#8",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "31",
                    "edge": true,
                    "parent": "1",
                    "source": "4",
                    "target": "18",
                    "mxObjectId": "mxCell#12",
                    "collapsed": false
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 2",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": -14
                }
            },
            "style": "transition;",
            "id": "5",
            "vertex": true,
            "connectable": true,
            "parent": "3",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#9",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "33",
                    "edge": true,
                    "parent": "1",
                    "source": "5",
                    "target": "23",
                    "mxObjectId": "mxCell#13"
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 3",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": 16
                }
            },
            "style": "transition;",
            "id": "6",
            "vertex": true,
            "connectable": true,
            "parent": "3",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#10",
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 4",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": 46
                }
            },
            "style": "transition;",
            "id": "7",
            "vertex": true,
            "connectable": true,
            "parent": "3",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#11"
        },
        {
            "value": "PORT_Actions",
            "geometry": {
                "x": 1,
                "y": 0.75,
                "width": 32,
                "height": 32,
                "relative": true,
                "offset": {
                    "x": -16,
                    "y": -8
                }
            },
            "style": "port_actions;image=https://cloud.githubusercontent.com/assets/6003532/20865730/414b06a2-ba55-11e6-85f9-1ccbc0c30a73.png;align=right;imageAlign=right;spacingRight=18",
            "id": "8",
            "vertex": true,
            "connectable": true,
            "parent": "2",
            "source": null,
            "target": null,
            "collapsed": false,
            "children": [
                {
                    "value": {
                        "name": "action 1",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": -44
                        }
                    },
                    "style": "action;",
                    "id": "9",
                    "vertex": true,
                    "connectable": true,
                    "parent": "8",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#15"
                },
                {
                    "value": {
                        "name": "action 2",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": -14
                        }
                    },
                    "style": "action;",
                    "id": "10",
                    "vertex": true,
                    "connectable": true,
                    "parent": "8",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#16",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "32",
                            "edge": true,
                            "parent": "1",
                            "source": "10",
                            "target": "16",
                            "mxObjectId": "mxCell#19",
                            "collapsed": false
                        }
                    ],
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "action 3",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": 16
                        }
                    },
                    "style": "action;",
                    "id": "11",
                    "vertex": true,
                    "connectable": true,
                    "parent": "8",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#17",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "35",
                            "edge": true,
                            "parent": "1",
                            "source": "11",
                            "target": "24",
                            "mxObjectId": "mxCell#21",
                            "collapsed": true
                        }
                    ],
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "action 4",
                        "type": "state"
                    },
                    "geometry": {
                        "x": 1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": 2,
                            "y": 46
                        }
                    },
                    "style": "action;",
                    "id": "12",
                    "vertex": true,
                    "connectable": true,
                    "parent": "8",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#18",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "34",
                            "edge": true,
                            "parent": "1",
                            "source": "12",
                            "target": "29",
                            "mxObjectId": "mxCell#20",
                            "collapsed": false
                        }
                    ],
                    "collapsed": true
                }
            ],
            "edges": [],
            "mxObjectId": "mxCell#14"
        },
        {
            "value": {
                "name": "action 1",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": -44
                }
            },
            "style": "action;",
            "id": "9",
            "vertex": true,
            "connectable": true,
            "parent": "8",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#15"
        },
        {
            "value": {
                "name": "action 2",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": -14
                }
            },
            "style": "action;",
            "id": "10",
            "vertex": true,
            "connectable": true,
            "parent": "8",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#16",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "32",
                    "edge": true,
                    "parent": "1",
                    "source": "10",
                    "target": "16",
                    "mxObjectId": "mxCell#19",
                    "collapsed": false
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "action 3",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": 16
                }
            },
            "style": "action;",
            "id": "11",
            "vertex": true,
            "connectable": true,
            "parent": "8",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#17",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "35",
                    "edge": true,
                    "parent": "1",
                    "source": "11",
                    "target": "24",
                    "mxObjectId": "mxCell#21",
                    "collapsed": true
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "action 4",
                "type": "state"
            },
            "geometry": {
                "x": 1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": 2,
                    "y": 46
                }
            },
            "style": "action;",
            "id": "12",
            "vertex": true,
            "connectable": true,
            "parent": "8",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#18",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "34",
                    "edge": true,
                    "parent": "1",
                    "source": "12",
                    "target": "29",
                    "mxObjectId": "mxCell#20",
                    "collapsed": false
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "View 1",
                "mainNode": "false",
                "type": "component"
            },
            "geometry": {
                "x": 1100,
                "y": 30,
                "width": 200,
                "height": 60,
                "relative": false
            },
            "style": "",
            "id": "13",
            "vertex": true,
            "connectable": false,
            "parent": "1",
            "source": null,
            "target": null,
            "children": [
                {
                    "value": "PORT_Transitions",
                    "geometry": {
                        "x": 0,
                        "y": 0.25,
                        "width": 32,
                        "height": 32,
                        "relative": true,
                        "offset": {
                            "x": -16,
                            "y": 0
                        }
                    },
                    "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
                    "id": "14",
                    "vertex": true,
                    "connectable": true,
                    "parent": "13",
                    "source": null,
                    "target": null,
                    "collapsed": false,
                    "children": [
                        {
                            "value": {
                                "name": "transition 1",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": -44
                                }
                            },
                            "style": "transition;",
                            "id": "15",
                            "vertex": true,
                            "connectable": true,
                            "parent": "14",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#24"
                        },
                        {
                            "value": {
                                "name": "transition 2",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": -14
                                }
                            },
                            "style": "transition;",
                            "id": "16",
                            "vertex": true,
                            "connectable": true,
                            "parent": "14",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#25",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "32",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "10",
                                    "target": "16",
                                    "mxObjectId": "mxCell#19",
                                    "collapsed": false
                                }
                            ],
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 3",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": 16
                                }
                            },
                            "style": "transition;",
                            "id": "17",
                            "vertex": true,
                            "connectable": true,
                            "parent": "14",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#26",
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 4",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": 46
                                }
                            },
                            "style": "transition;",
                            "id": "18",
                            "vertex": true,
                            "connectable": true,
                            "parent": "14",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#27",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "31",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "4",
                                    "target": "18",
                                    "mxObjectId": "mxCell#12",
                                    "collapsed": false
                                }
                            ],
                            "collapsed": true
                        }
                    ],
                    "edges": [],
                    "mxObjectId": "mxCell#23"
                }
            ],
            "mxObjectId": "mxCell#22"
        },
        {
            "value": "PORT_Transitions",
            "geometry": {
                "x": 0,
                "y": 0.25,
                "width": 32,
                "height": 32,
                "relative": true,
                "offset": {
                    "x": -16,
                    "y": 0
                }
            },
            "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
            "id": "14",
            "vertex": true,
            "connectable": true,
            "parent": "13",
            "source": null,
            "target": null,
            "collapsed": false,
            "children": [
                {
                    "value": {
                        "name": "transition 1",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": -44
                        }
                    },
                    "style": "transition;",
                    "id": "15",
                    "vertex": true,
                    "connectable": true,
                    "parent": "14",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#24"
                },
                {
                    "value": {
                        "name": "transition 2",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": -14
                        }
                    },
                    "style": "transition;",
                    "id": "16",
                    "vertex": true,
                    "connectable": true,
                    "parent": "14",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#25",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "32",
                            "edge": true,
                            "parent": "1",
                            "source": "10",
                            "target": "16",
                            "mxObjectId": "mxCell#19",
                            "collapsed": false
                        }
                    ],
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 3",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": 16
                        }
                    },
                    "style": "transition;",
                    "id": "17",
                    "vertex": true,
                    "connectable": true,
                    "parent": "14",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#26",
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 4",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": 46
                        }
                    },
                    "style": "transition;",
                    "id": "18",
                    "vertex": true,
                    "connectable": true,
                    "parent": "14",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#27",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "31",
                            "edge": true,
                            "parent": "1",
                            "source": "4",
                            "target": "18",
                            "mxObjectId": "mxCell#12",
                            "collapsed": false
                        }
                    ],
                    "collapsed": true
                }
            ],
            "edges": [],
            "mxObjectId": "mxCell#23"
        },
        {
            "value": {
                "name": "transition 1",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": -44
                }
            },
            "style": "transition;",
            "id": "15",
            "vertex": true,
            "connectable": true,
            "parent": "14",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#24"
        },
        {
            "value": {
                "name": "transition 2",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": -14
                }
            },
            "style": "transition;",
            "id": "16",
            "vertex": true,
            "connectable": true,
            "parent": "14",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#25",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "32",
                    "edge": true,
                    "parent": "1",
                    "source": "10",
                    "target": "16",
                    "mxObjectId": "mxCell#19",
                    "collapsed": false
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 3",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": 16
                }
            },
            "style": "transition;",
            "id": "17",
            "vertex": true,
            "connectable": true,
            "parent": "14",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#26",
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 4",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": 46
                }
            },
            "style": "transition;",
            "id": "18",
            "vertex": true,
            "connectable": true,
            "parent": "14",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#27",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "31",
                    "edge": true,
                    "parent": "1",
                    "source": "4",
                    "target": "18",
                    "mxObjectId": "mxCell#12",
                    "collapsed": false
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "View 2",
                "mainNode": "false",
                "type": "component"
            },
            "geometry": {
                "x": 1100,
                "y": 170,
                "width": 200,
                "height": 60,
                "relative": false
            },
            "style": "",
            "id": "19",
            "vertex": true,
            "connectable": false,
            "parent": "1",
            "source": null,
            "target": null,
            "children": [
                {
                    "value": "PORT_Transitions",
                    "geometry": {
                        "x": 0,
                        "y": 0.25,
                        "width": 32,
                        "height": 32,
                        "relative": true,
                        "offset": {
                            "x": -16,
                            "y": 0
                        }
                    },
                    "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
                    "id": "20",
                    "vertex": true,
                    "connectable": true,
                    "parent": "19",
                    "source": null,
                    "target": null,
                    "collapsed": false,
                    "children": [
                        {
                            "value": {
                                "name": "transition 1",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": -44
                                }
                            },
                            "style": "transition;",
                            "id": "21",
                            "vertex": true,
                            "connectable": true,
                            "parent": "20",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#30"
                        },
                        {
                            "value": {
                                "name": "transition 2",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": -14
                                }
                            },
                            "style": "transition;",
                            "id": "22",
                            "vertex": true,
                            "connectable": true,
                            "parent": "20",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#31",
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 3",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": 16
                                }
                            },
                            "style": "transition;",
                            "id": "23",
                            "vertex": true,
                            "connectable": true,
                            "parent": "20",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#32",
                            "collapsed": false,
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "33",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "5",
                                    "target": "23",
                                    "mxObjectId": "mxCell#13"
                                }
                            ]
                        },
                        {
                            "value": {
                                "name": "transition 4",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": 46
                                }
                            },
                            "style": "transition;",
                            "id": "24",
                            "vertex": true,
                            "connectable": true,
                            "parent": "20",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#33",
                            "collapsed": false,
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "35",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "11",
                                    "target": "24",
                                    "mxObjectId": "mxCell#21",
                                    "collapsed": true
                                }
                            ]
                        }
                    ],
                    "edges": [],
                    "mxObjectId": "mxCell#29"
                }
            ],
            "mxObjectId": "mxCell#28"
        },
        {
            "value": "PORT_Transitions",
            "geometry": {
                "x": 0,
                "y": 0.25,
                "width": 32,
                "height": 32,
                "relative": true,
                "offset": {
                    "x": -16,
                    "y": 0
                }
            },
            "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
            "id": "20",
            "vertex": true,
            "connectable": true,
            "parent": "19",
            "source": null,
            "target": null,
            "collapsed": false,
            "children": [
                {
                    "value": {
                        "name": "transition 1",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": -44
                        }
                    },
                    "style": "transition;",
                    "id": "21",
                    "vertex": true,
                    "connectable": true,
                    "parent": "20",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#30"
                },
                {
                    "value": {
                        "name": "transition 2",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": -14
                        }
                    },
                    "style": "transition;",
                    "id": "22",
                    "vertex": true,
                    "connectable": true,
                    "parent": "20",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#31",
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 3",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": 16
                        }
                    },
                    "style": "transition;",
                    "id": "23",
                    "vertex": true,
                    "connectable": true,
                    "parent": "20",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#32",
                    "collapsed": false,
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "33",
                            "edge": true,
                            "parent": "1",
                            "source": "5",
                            "target": "23",
                            "mxObjectId": "mxCell#13"
                        }
                    ]
                },
                {
                    "value": {
                        "name": "transition 4",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": 46
                        }
                    },
                    "style": "transition;",
                    "id": "24",
                    "vertex": true,
                    "connectable": true,
                    "parent": "20",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#33",
                    "collapsed": false,
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "35",
                            "edge": true,
                            "parent": "1",
                            "source": "11",
                            "target": "24",
                            "mxObjectId": "mxCell#21",
                            "collapsed": true
                        }
                    ]
                }
            ],
            "edges": [],
            "mxObjectId": "mxCell#29"
        },
        {
            "value": {
                "name": "transition 1",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": -44
                }
            },
            "style": "transition;",
            "id": "21",
            "vertex": true,
            "connectable": true,
            "parent": "20",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#30"
        },
        {
            "value": {
                "name": "transition 2",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": -14
                }
            },
            "style": "transition;",
            "id": "22",
            "vertex": true,
            "connectable": true,
            "parent": "20",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#31",
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 3",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": 16
                }
            },
            "style": "transition;",
            "id": "23",
            "vertex": true,
            "connectable": true,
            "parent": "20",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#32",
            "collapsed": false,
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "33",
                    "edge": true,
                    "parent": "1",
                    "source": "5",
                    "target": "23",
                    "mxObjectId": "mxCell#13"
                }
            ]
        },
        {
            "value": {
                "name": "transition 4",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": 46
                }
            },
            "style": "transition;",
            "id": "24",
            "vertex": true,
            "connectable": true,
            "parent": "20",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#33",
            "collapsed": false,
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "35",
                    "edge": true,
                    "parent": "1",
                    "source": "11",
                    "target": "24",
                    "mxObjectId": "mxCell#21",
                    "collapsed": true
                }
            ]
        },
        {
            "value": {
                "name": "View 3",
                "mainNode": "false",
                "type": "component"
            },
            "geometry": {
                "x": 1100,
                "y": 310,
                "width": 200,
                "height": 60,
                "relative": false
            },
            "style": "",
            "id": "25",
            "vertex": true,
            "connectable": false,
            "parent": "1",
            "source": null,
            "target": null,
            "children": [
                {
                    "value": "PORT_Transitions",
                    "geometry": {
                        "x": 0,
                        "y": 0.25,
                        "width": 32,
                        "height": 32,
                        "relative": true,
                        "offset": {
                            "x": -16,
                            "y": 0
                        }
                    },
                    "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
                    "id": "26",
                    "vertex": true,
                    "connectable": true,
                    "parent": "25",
                    "source": null,
                    "target": null,
                    "collapsed": false,
                    "children": [
                        {
                            "value": {
                                "name": "transition 1",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": -44
                                }
                            },
                            "style": "transition;",
                            "id": "27",
                            "vertex": true,
                            "connectable": true,
                            "parent": "26",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#36"
                        },
                        {
                            "value": {
                                "name": "transition 2",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": -14
                                }
                            },
                            "style": "transition;",
                            "id": "28",
                            "vertex": true,
                            "connectable": true,
                            "parent": "26",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#37",
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 3",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": 16
                                }
                            },
                            "style": "transition;",
                            "id": "29",
                            "vertex": true,
                            "connectable": true,
                            "parent": "26",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#38",
                            "edges": [
                                {
                                    "value": "Edge",
                                    "geometry": {
                                        "x": 0,
                                        "y": 0,
                                        "width": 0,
                                        "height": 0,
                                        "relative": true
                                    },
                                    "id": "34",
                                    "edge": true,
                                    "parent": "1",
                                    "source": "12",
                                    "target": "29",
                                    "mxObjectId": "mxCell#20",
                                    "collapsed": false
                                }
                            ],
                            "collapsed": true
                        },
                        {
                            "value": {
                                "name": "transition 4",
                                "type": "state"
                            },
                            "geometry": {
                                "x": -1,
                                "y": 0,
                                "width": 200,
                                "height": 30,
                                "relative": true,
                                "offset": {
                                    "x": -170,
                                    "y": 46
                                }
                            },
                            "style": "transition;",
                            "id": "30",
                            "vertex": true,
                            "connectable": true,
                            "parent": "26",
                            "source": null,
                            "target": null,
                            "mxObjectId": "mxCell#39"
                        }
                    ],
                    "edges": [],
                    "mxObjectId": "mxCell#35"
                }
            ],
            "mxObjectId": "mxCell#34"
        },
        {
            "value": "PORT_Transitions",
            "geometry": {
                "x": 0,
                "y": 0.25,
                "width": 32,
                "height": 32,
                "relative": true,
                "offset": {
                    "x": -16,
                    "y": 0
                }
            },
            "style": "port_transitions;image=https://cloud.githubusercontent.com/assets/6003532/20865665/61f4e1ea-ba53-11e6-83ce-806ce115bd6f.png;align=right;imageAlign=right;spacingRight=18",
            "id": "26",
            "vertex": true,
            "connectable": true,
            "parent": "25",
            "source": null,
            "target": null,
            "collapsed": false,
            "children": [
                {
                    "value": {
                        "name": "transition 1",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": -44
                        }
                    },
                    "style": "transition;",
                    "id": "27",
                    "vertex": true,
                    "connectable": true,
                    "parent": "26",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#36"
                },
                {
                    "value": {
                        "name": "transition 2",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": -14
                        }
                    },
                    "style": "transition;",
                    "id": "28",
                    "vertex": true,
                    "connectable": true,
                    "parent": "26",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#37",
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 3",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": 16
                        }
                    },
                    "style": "transition;",
                    "id": "29",
                    "vertex": true,
                    "connectable": true,
                    "parent": "26",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#38",
                    "edges": [
                        {
                            "value": "Edge",
                            "geometry": {
                                "x": 0,
                                "y": 0,
                                "width": 0,
                                "height": 0,
                                "relative": true
                            },
                            "id": "34",
                            "edge": true,
                            "parent": "1",
                            "source": "12",
                            "target": "29",
                            "mxObjectId": "mxCell#20",
                            "collapsed": false
                        }
                    ],
                    "collapsed": true
                },
                {
                    "value": {
                        "name": "transition 4",
                        "type": "state"
                    },
                    "geometry": {
                        "x": -1,
                        "y": 0,
                        "width": 200,
                        "height": 30,
                        "relative": true,
                        "offset": {
                            "x": -170,
                            "y": 46
                        }
                    },
                    "style": "transition;",
                    "id": "30",
                    "vertex": true,
                    "connectable": true,
                    "parent": "26",
                    "source": null,
                    "target": null,
                    "mxObjectId": "mxCell#39"
                }
            ],
            "edges": [],
            "mxObjectId": "mxCell#35"
        },
        {
            "value": {
                "name": "transition 1",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": -44
                }
            },
            "style": "transition;",
            "id": "27",
            "vertex": true,
            "connectable": true,
            "parent": "26",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#36"
        },
        {
            "value": {
                "name": "transition 2",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": -14
                }
            },
            "style": "transition;",
            "id": "28",
            "vertex": true,
            "connectable": true,
            "parent": "26",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#37",
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 3",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": 16
                }
            },
            "style": "transition;",
            "id": "29",
            "vertex": true,
            "connectable": true,
            "parent": "26",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#38",
            "edges": [
                {
                    "value": "Edge",
                    "geometry": {
                        "x": 0,
                        "y": 0,
                        "width": 0,
                        "height": 0,
                        "relative": true
                    },
                    "id": "34",
                    "edge": true,
                    "parent": "1",
                    "source": "12",
                    "target": "29",
                    "mxObjectId": "mxCell#20",
                    "collapsed": false
                }
            ],
            "collapsed": true
        },
        {
            "value": {
                "name": "transition 4",
                "type": "state"
            },
            "geometry": {
                "x": -1,
                "y": 0,
                "width": 200,
                "height": 30,
                "relative": true,
                "offset": {
                    "x": -170,
                    "y": 46
                }
            },
            "style": "transition;",
            "id": "30",
            "vertex": true,
            "connectable": true,
            "parent": "26",
            "source": null,
            "target": null,
            "mxObjectId": "mxCell#39"
        },
        {
            "value": "Edge",
            "geometry": {
                "x": 0,
                "y": 0,
                "width": 0,
                "height": 0,
                "relative": true
            },
            "id": "31",
            "edge": true,
            "parent": "1",
            "source": "4",
            "target": "18",
            "mxObjectId": "mxCell#12",
            "collapsed": false
        },
        {
            "value": "Edge",
            "geometry": {
                "x": 0,
                "y": 0,
                "width": 0,
                "height": 0,
                "relative": true
            },
            "id": "32",
            "edge": true,
            "parent": "1",
            "source": "10",
            "target": "16",
            "mxObjectId": "mxCell#19",
            "collapsed": false
        },
        {
            "value": "Edge",
            "geometry": {
                "x": 0,
                "y": 0,
                "width": 0,
                "height": 0,
                "relative": true
            },
            "id": "33",
            "edge": true,
            "parent": "1",
            "source": "5",
            "target": "23",
            "mxObjectId": "mxCell#13"
        },
        {
            "value": "Edge",
            "geometry": {
                "x": 0,
                "y": 0,
                "width": 0,
                "height": 0,
                "relative": true
            },
            "id": "34",
            "edge": true,
            "parent": "1",
            "source": "12",
            "target": "29",
            "mxObjectId": "mxCell#20",
            "collapsed": false
        },
        {
            "value": "Edge",
            "geometry": {
                "x": 0,
                "y": 0,
                "width": 0,
                "height": 0,
                "relative": true
            },
            "id": "35",
            "edge": true,
            "parent": "1",
            "source": "11",
            "target": "24",
            "mxObjectId": "mxCell#21",
            "collapsed": true
        }
    ]
})}
];


@Injectable({
  providedIn: 'root'
})

export class GraphService {

  currentGraphId: number;

  constructor(private messageService: MessageService) {
    this.currentGraphId = 0;
  }

  // getGraphDescriptions():  Observable<GraphDescription[]> {
  //    this.messageService.add('GraphService: fetched descriptions.');
  //    retrun of(GRAPH_DESCRITPION);
  // }

  getCurrentGraph():  Observable<Graph>{
    return this.getGraph(this.currentGraphId);
  }

  getGraph(id :number):  Observable<Graph>{
     this.messageService.add('GraphService: fetched graph#' + id +".");
     return of(GRAPHS[id]);
  }

  save(): void {
     this.messageService.add('GraphService: current graph saved.');
  }
}
