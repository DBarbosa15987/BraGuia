import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class Media extends Model {
  static table = 'media'

  static associations = {
    media: { type: 'belongs_to', foreignKey: 'pin_id' },
}

  @text('media_id') media_id
  @text('file') file

}