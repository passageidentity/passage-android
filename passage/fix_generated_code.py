import os

# Define a list of tuples for replacements where each tuple is ('file_path', 'old_text', 'new_text')
replacements = [
    
  # Authenticator attachment "cross-platform" gets misleading name "crossMinusPlatform":
  (
    './generated/src/main/kotlin/id/passage/android/model/AuthenticatorAttachment.kt',
    'crossMinusPlatform',
    'crossPlatform'
  ),

  # appleOauth2Callback functions wrongfully assume "error" is NOT null:
  (
    './generated/src/main/kotlin/id/passage/android/api/OAuth2API.kt',
    '"error" to PartConfig(body = error.value, headers = mutableMapOf())',
    '"error" to PartConfig(body = error?.value, headers = mutableMapOf())'
  ),

  # User model requires `webauthn_types` property, but webauthn start returns user WITHOUT it, so we must make it optional.
  (
    './generated/src/main/kotlin/id/passage/android/model/User.kt',
    '''val webauthnTypes: kotlin.collections.List<WebAuthnType>

''',
    '''val webauthnTypes: kotlin.collections.List<WebAuthnType>?

'''
  ),

  # Some endpoints return a user with a status set to "". This is incompatible with the spec, so we have to add the statusUnavailable option.
  (
    './generated/src/main/kotlin/id/passage/android/model/UserStatus.kt',
    '''@Json(name = "pending")
    pending("pending");
''',
    '''@Json(name = "pending")
    pending("pending"),

    @Json(name = "")
    statusUnavailable("");
    '''
  ),

  # Add more replacements here as needed
  # ('oldText', 'newText'),
]

def replace_in_file(replacement):
  file_path, old_text, new_text = replacement
  with open(file_path, 'r', encoding='utf-8') as file:
    content = file.read()

  updated_content = content
  # for old_text, new_text in replacements:
  updated_content = updated_content.replace(old_text, new_text)
  
  if content != updated_content:
    with open(file_path, 'w', encoding='utf-8') as file:
      file.write(updated_content)
    print(f"Updated {file_path}")

for replacement in replacements:
  replace_in_file(replacement)
