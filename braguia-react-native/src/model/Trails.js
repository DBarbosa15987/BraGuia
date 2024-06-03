import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class Trails extends Model {
    static table = 'trails'
  
    @text('trail_id') trail_id
    @text('img') img
    @text('name') name
    @text('description') description
    @text('duration') duration
    @text('difficulty') difficulty
    // FIX: add relations
  }