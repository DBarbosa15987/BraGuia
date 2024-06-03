import {appSchema, tableSchema} from '@nozbe/watermelondb';

export default appSchema({
  version: 1,
  tables: [
    tableSchema({
      name: 'pins',
      columns: [
        {name: 'pin_id', type: 'number'},
        {name: 'name', type: 'string'},
        {name: 'desc', type: 'string'},
        {name: 'lat', type: 'number'},
        {name: 'lng', type: 'number'},
        {name: 'alt', type: 'number'},
      ],
    }),
    tableSchema({
      name: 'trails',
      columns: [
        {name: 'trail_id', type: 'number'},
        {name: 'img', type: 'string'},
        {name: 'name', type: 'string'},
        {name: 'description', type: 'string'},
        {name: 'duration', type: 'number'},
        {name: 'difficulty', type: 'string'},
      ],
    }),
    tableSchema({
      name: 'media',
      columns: [
        {name: 'media_id', type: 'number'},
        {name: 'file', type: 'string'},
        {name: 'type', type: 'string'},
      ],
    }),
    tableSchema({
      name: 'rel_pin',
      columns: [
        {name: 'rel_pin_id', type: 'number'},
        {name: 'value', type: 'string'},
        {name: 'attrib', type: 'string'},
      ],
    }),
    tableSchema({
      name: 'edges',
      columns: [
        {name: 'edge_id', type: 'number'},
        {name: 'transport', type: 'string'},
        {name: 'duration', type: 'number'},
        {name: 'description', type: 'number'},
      ],
    }),
    tableSchema({
      name: 'rel_trail',
      columns: [
        {name: 'rel_trail_id', type: 'number'},
        {name: 'value', type: 'string'},
        {name: 'attrib', type: 'string'},
      ],
    }),
    tableSchema({
      name: 'app_info',
      columns: [
        {name: 'app_name', type: 'string'},
        {name: 'description', type: 'string'},
        {name: 'landing_page_text', type: 'string'},
      ],
    }),
    tableSchema({
      name: 'app_info',
      columns: [
        {name: 'app_name', type: 'string'},
        {name: 'description', type: 'string'},
        {name: 'landing_page_text', type: 'string'},
      ],
    }),
    tableSchema({
      name: 'contacts',
      columns: [
        {name: 'name', type: 'string'},
        {name: 'phone', type: 'string'},
        {name: 'url', type: 'string'},
        {name: 'mail', type: 'string'},
        {name: 'desc', type: 'string'},
        {name: 'app', type: 'string'},
      ],
    }),
  ],
});
