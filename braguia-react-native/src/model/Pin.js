import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class Pin extends Model {
  static table = 'pins'
  static associations = {
    rel_pins: { type: 'has_many', foreignKey: 'pin_id' },
    media: { type: 'has_many', foreignKey: 'pin_id' }
  }

  @children('rel_pins') relPins
  @children('media') media

  @field('pin_id') pinId
  @text('name') name
  @text('desc') desc
  @field('lat') lat
  @field('lng') lng
  @field('alt') alt
  
}

