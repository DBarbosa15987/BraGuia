import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class RelTrail extends Model {
    static table = 'rel_trail'
  
    @text('rel_trail_id') rel_trail_id
    @text('value') value
    @text('attrib') attrib
    // FIX: add relations
  }