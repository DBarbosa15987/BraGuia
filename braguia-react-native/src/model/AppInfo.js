import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class AppInfo extends Model {
    static table = 'app_info'
  
    @text('app_name') app_name
    @text('description') description
    @text('landing_page_text') landing_page_text
    // FIX: add relations
  }
  