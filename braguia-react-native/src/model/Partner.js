import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class Partner extends Model {
    static table = 'partners'
  
    @text('name') name
    @text('phone') phone
    @text('url') url
    @text('mail') mail
    @text('desc') desc
    @text('app') app
  }