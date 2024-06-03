import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class Edge extends Model {
    static table = 'edges'
  
    @text('edge_id') edge_id
    @text('transport') transport
    @text('duration') duration
    @text('description') description
    // FIX: add relations
  }